package com.meiya;

public final class NettyMessage {

	private NettyMessageHeader header;
	private int bytesSize;
	private boolean isEnd;
	private byte[] bytes;
	private Object body;

	public NettyMessageHeader getHeader() {
		return header;
	}

	public void setHeader(NettyMessageHeader header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString(){
		return "NettyMessage [header="+header+"]";
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public int getBytesSize() {
		return bytesSize;
	}

	public void setBytesSize(int bytesSize) {
		this.bytesSize = bytesSize;
	}

	public boolean isEnd() {
		return isEnd;
	}

	public void setEnd(boolean end) {
		isEnd = end;
	}
}
