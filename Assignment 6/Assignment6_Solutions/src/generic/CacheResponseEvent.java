package generic;

public class CacheResponseEvent extends Event{

    int value;
	
	public CacheResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}

// package generic;

// public class CacheResponseEvent extends Event{

//     boolean hitOrMiss;
	
// 	public CacheResponseEvent(long eventTime, Element requestingElement, Element processingElement, boolean hitOrMiss) {
// 		super(eventTime, EventType.CacheResponse, requestingElement, processingElement);
// 		this.hitOrMiss = hitOrMiss;
// 	}

// 	public boolean getValue() {
// 		return hitOrMiss;
// 	}

// 	public void setValue(boolean hitOrMiss) {
// 		this.hitOrMiss = hitOrMiss;
// 	}
// }

