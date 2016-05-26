package gui;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Contents") // another mapping
public class Contents { 
 @XStreamAlias("Key") // 
 String fileName;
 
 @XStreamAlias("Size") // 
 String fileSize;
 
 @XStreamAlias("LastModified") // 
 String createdAt;
}