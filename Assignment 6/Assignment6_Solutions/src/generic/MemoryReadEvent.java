package generic;

public class MemoryReadEvent extends Event {

	int addressToReadFrom;

	Element requestingFor;
	
	public MemoryReadEvent(long eventTime, Element requestingElement, Element requestingFor, Element processingElement, int address) {
		super(eventTime, EventType.MemoryRead, requestingElement, processingElement);
		this.addressToReadFrom = address;
		this.requestingFor= requestingFor;
	}

	public int getAddressToReadFrom() {
		return addressToReadFrom;
	}

	public void setAddressToReadFrom(int addressToReadFrom) {
		this.addressToReadFrom = addressToReadFrom;
	}

	public Element getReqestingForElement() {
		return this.requestingFor;
	}

	public void setRequestingFor(Element requestingFor) {
		this.requestingFor = requestingFor;
	}
}
