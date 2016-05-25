package com.github.axet.wget;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadMultipartError;

public class Downloader implements Runnable {
	private String directory = System.getProperty("user.home") + "/Downloads";
	private String url;
	private Runnable notify;

	public Downloader() {

	}

	public void setURL(String url) {
		this.url = url;
	}

	public String getURL() {
		return this.url;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getDirectory() {
		return this.directory;
	}

	AtomicBoolean stop = new AtomicBoolean(false);
	DownloadInfo info;
	long last;
	SpeedInfo speedInfo = new SpeedInfo();
	private boolean shutDown;

	public SpeedInfo getSpeedInfo() {
		return speedInfo;
	}

	public DownloadInfo info() {
		return info;
	}
	public static String formatSpeed(long s) {
		if (s > 0.1 * 1024 * 1024 * 1024) {
			float f = s / 1024f / 1024f / 1024f;
			return String.format("%.1f GB", f);
		} else if (s > 0.1 * 1024 * 1024) {
			float f = s / 1024f / 1024f;
			return String.format("%.1f MB", f);
		} else {
			float f = s / 1024f;
			return String.format("%.1f kb", f);
		}
	}



	public void run() {
		try {
			notify = new Runnable() {
				private volatile boolean shutdown;
				@Override
				public void run() {
						// notify app or save download state
						// you can extract information from DownloadInfo info;
						switch (info.getState()) {
						case EXTRACTING:
						case EXTRACTING_DONE:
							System.out.println(info.getState());
							break;
						case DONE:
							// finish speed calculation by adding remaining bytes speed
							speedInfo.end(info.getCount());
							// print speed
							System.out.println(String.format("%s average speed (%s)", info.getState(), formatSpeed(speedInfo.getAverageSpeed())));
							break;
						case RETRYING:
							System.out.println(info.getState() + " " + info.getDelay());
							break;
						case DOWNLOADING:
							speedInfo.step(info.getCount());
							long now = System.currentTimeMillis();
							if (now - 1000 > last) {
								last = now;

								String parts = "";

								for (Part p : info.getParts()) {
									if (p.getState().equals(States.DOWNLOADING) && !this.shutdown) {
										parts += String.format("Part#%d(%.2f) ", p.getNumber(),
												p.getCount() / (float) p.getLength());
									}
								}

								float p = info.getCount() / (float) info.getLength();

								System.out.println(String.format("%.2f %s (%s / %s)", p, parts,
										formatSpeed(speedInfo.getCurrentSpeed()),
										formatSpeed(speedInfo.getAverageSpeed())));
							}
							
							break;
						default:
							break;
						}
				}
				public void shutdown() {
			        this.shutdown = true;
			    }
			};

			// choice file
			URL url = new URL("http://djhrn44g26er2.cloudfront.net/" + this.url);
			// initialize url information object with or without proxy
			// info = new DownloadInfo(url, new ProxyInfo("proxy_addr", 8080, "login", "password"));
			info = new DownloadInfo(url);
			// extract information from the web
			info.extract(stop, notify);
			// enable multipart download
			info.enableMultipart();
			// Choice target file or set download folder
			String filename = url.getPath();

			File f = new File(directory + filename);
			int version = 1;
			String versionFileName = url.getFile().substring(0, url.getPath().length() - 4);
			String newFilename = versionFileName;

			while (f.exists()) {
				newFilename= versionFileName + "(" + version + ")";
				f = new File(directory + newFilename + ".tif");
				version++;
			}

			File target = f;
			// create wget downloader
			WGet w = new WGet(info, target);

			// init speedinfo
			speedInfo.start(0);
			// will blocks until download finishes
			w.download(stop, notify);
		} catch (DownloadMultipartError e) {
			for (Part p : e.getInfo().getParts()) {
				Throwable ee = p.getException();
				if (ee != null)
					ee.printStackTrace();
			}
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setShutdown(boolean b) {
		this.shutDown = b;
	}
}