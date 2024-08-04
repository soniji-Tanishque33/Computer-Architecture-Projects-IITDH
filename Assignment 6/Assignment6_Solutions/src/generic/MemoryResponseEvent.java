package generic;

public class MemoryResponseEvent extends Event {

	int value;
	int addressToReadFrom;

	Element requestedFor;
	
	public MemoryResponseEvent(long eventTime, Element requestingElement, Element requestingFor, Element processingElement, int value, int address) {
		super(eventTime, EventType.MemoryResponse, requestingElement, processingElement);
		this.value = value;
		this.addressToReadFrom = address;
		this.requestedFor = requestingFor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}

	public Element getReqestingForElement() {
		return this.requestedFor;
	}

	public void setRequestingFor(Element requestedFor) {
		this.requestedFor = requestedFor;
	}

}
