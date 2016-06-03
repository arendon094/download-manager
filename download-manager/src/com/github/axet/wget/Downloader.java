/**
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * This code is produced by the California State University of Los Angeles for
 * the Jet Propulsion Laboratory (JPL).
 */
package com.github.axet.wget;

import java.io.File;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;

import com.github.axet.wget.info.DownloadInfo;
import com.github.axet.wget.info.URLInfo;
import com.github.axet.wget.info.DownloadInfo.Part;
import com.github.axet.wget.info.DownloadInfo.Part.States;
import com.github.axet.wget.info.ex.DownloadInterruptedError;
import com.github.axet.wget.info.ex.DownloadMultipartError;

/**
 * This class consists of methods which are run by the download-manager
 * upon download initiation and whose purpose is to connect to an AWS
 * CloudFront CDN and complete the download of a GeoTIFF file from
 * an S3 bucket which may or may not be cached at the AWS CloudFront CDN.
 * 
 * @author Rowan Edge
 * @author Mariah Martinez
 * @author Gregory Miles
 */

public class Downloader implements Runnable {
	private String directory = System.getProperty("user.home") + "/Downloads";
	private String url;
	private Runnable notify;
	private volatile boolean pause;
	private boolean first;
	private File target;

	public Downloader() {
		this.pause = false;
		this.first = true;
	}

	public String getName() {
		if (target != null)
			return target.getName();
		else
			return this.url;
	}

	public long getSize() {
		if (info != null)
			return info.getCount();
		else
			return 0;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean getPause() {
		return this.pause;
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

	public void setStop() {
		stop.set(true);
	}

	AtomicBoolean stop = new AtomicBoolean(false);
	DownloadInfo info;
	long last;
	SpeedInfo speedInfo = new SpeedInfo();

	public SpeedInfo getSpeedInfo() {
		return speedInfo;
	}

	public DownloadInfo info() {
		return info;
	}

	/**
 	 * Converts a input size given in bytes to a human readable form in
 	 * KB, MB or GB.  If size is larger than GB the behavoir is undefined.
	 *
	 * @param  	s	A value in bytes.
	 * @return      A string representation of input value in KB, MB or GB.
	 */
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

	/**
 	 * Uses Downloader object settings directory and url to begin download
 	 * of item from S3 via AWS CloudFront CDN.
	 *
	 * @param  	void
	 * @return      void
	 */
	public void run() {
		try {
			notify = new Runnable() {
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
						// finish speed calculation by adding remaining bytes
						// speed
						speedInfo.end(info.getCount());
						// print speed
						System.out.println(String.format("%s average speed (%s)", info.getState(),
								formatSpeed(speedInfo.getAverageSpeed())));
						break;
					case RETRYING:
						System.out.println(info.getState() + " " + info.getDelay());
						break;
					case DOWNLOADING:
						speedInfo.step(info.getCount());
						long now = System.currentTimeMillis();
						if (now - 1000 > last) {
							last = now;
							
							if (info.getParts() == null) {
								if (info.getState().equals(States.DOWNLOADING)) {
									
								}
							} else {

							String parts = "";

							for (Part p : info.getParts()) {
								if (p.getState().equals(States.DOWNLOADING)) {
									parts += String.format("Part#%d(%.2f) ", p.getNumber(),
											p.getCount() / (float) p.getLength());
								}
							}

							float p = info.getCount() / (float) info.getLength();

							System.out.println(String.format("%.2f %s (%s / %s)", p, parts,
									formatSpeed(speedInfo.getCurrentSpeed()),
									formatSpeed(speedInfo.getAverageSpeed())));
							}
						}

						break;
					default:
						break;
					}
				}
			};

			// choice file, link to CloudFront and path for S3 bucket.
			URL url = new URL("http://djhrn44g26er2.cloudfront.net/" + this.url);
			// initialize url information object with or without proxy
			// info = new DownloadInfo(url, new ProxyInfo("proxy_addr", 8080,
			// "login", "password"));
			if (info == null) {
				info = new DownloadInfo(url);
				// extract information from the web
				info.extract(stop, notify);
				// enable multipart download
				info.enableMultipart();
			}
			// Choice target file or set download folder
			String filename = url.getPath();
			
			if (filename.contains("thumb")) {
				System.out.println("contains thumb");
				filename = filename.replace(".tif.thumb", "-thumb.tif");
				System.out.println(filename);
			}
			
			// This will choose where the file is being saved and name the initial file based on
			// the name in the S3 bucket.
			File f = new File(directory + filename);
			int version = 1;
			String versionFileName = url.getFile().substring(0, url.getPath().length() - 4);
			String newFilename = versionFileName;
				
			if (first) {
				while (f.exists()) {
					newFilename = versionFileName + "(" + version + ")";
					f = new File(directory + newFilename + ".tif");
					version++;
				}

				target = f;
			}
			// Cheap way to ensure that Downloader instance only checks for
			// adding new files on first Download start, otherwise we are
			// resuming.
			first = false;
			// create wget downloader
			WGet w = new WGet(info, target);

			// init speedinfo
			speedInfo.start(info.getCount());
			// will blocks until download finishes
			System.out.println("STARTING DOWNLOAD!!");
			
			// This controls the pause / resume of the download.
			// Each Downloader object instance is running inside a thread.
			// When that thread is interrupted, the pause state is enabled and the
			// thread will wait until another interrupt is received which will toggle the pause
			// state again.  This will continue until the DONE state is reached or the program
			// is shutdown.
			while (info.getState() != URLInfo.States.DONE) {
				try {
					synchronized (this) {
						while (this.pause)
							wait();
					}
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					// this.pause = false;
					// this.run();
					// this.pause = true;
					System.out.println("RESUMING");
					w = new WGet(info, target);
				}
				try {
					w.download(stop, notify);
				} catch (DownloadInterruptedError e) {
					System.out.println("DOWNLOAD PAUSED");
				}
			}

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
}
