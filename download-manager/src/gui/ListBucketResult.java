package gui;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ListBucketResult") // another mapping
public class ListBucketResult {
	@XStreamImplicit(itemFieldName = "Contents")
	List contents = new ArrayList();
}
