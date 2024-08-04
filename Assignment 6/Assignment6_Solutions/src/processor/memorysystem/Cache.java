package processor.memorysystem;

import generic.*;
import processor.Processor;
import processor.Clock;
import processor.pipeline.InstructionFetch;
import processor.pipeline.MemoryAccess;

public class Cache implements Element {

	Processor containingProcessor;

    static int line_size = 4;
    int NumberOfLines;

    int[] cache;
    int[] tag_array;
    long[] LRU_time;

    public Cache(Processor containingProcessor, int num_of_lines, int latency) {
        this.containingProcessor = containingProcessor;
        this.NumberOfLines = num_of_lines;
        this.cache = new int[this.NumberOfLines];
        this.tag_array = new int[this.NumberOfLines];
        this.LRU_time = new long[this.NumberOfLines];
        for (int i = 0; i < this.NumberOfLines; i++) {
            this.tag_array[i] = -1;
            this.LRU_time[i] = -1;
        }
    }

    public int cacheRead(int address) {

        for (int i = 0; i < this.NumberOfLines; i++) {
            if (address == tag_array[i]) {
                this.LRU_time[i] = Clock.getCurrentTime();
                return this.cache[i];
            }
        }

        return 0;
    }

    public void cacheWrite(int address, int value) {

        for (int i = 0; i < this.NumberOfLines; i++) {
            if (address == tag_array[i]) {
                this.LRU_time[i] = Clock.getCurrentTime();
                this.cache[i] = value;
                return;
            }
        }

        int Line_to_evict = 0;
        long min_time = LRU_time[0];
        for (int i = 0; i < this.NumberOfLines; i++) {
            if(this.LRU_time[i] < min_time){
                Line_to_evict = i;
                min_time = LRU_time[i];
            }
        }

        this.cache[Line_to_evict] = value;
        this.tag_array[Line_to_evict] = address;
        this.LRU_time[Line_to_evict] = Clock.getCurrentTime();

    }

    @Override
    public void handleEvent(Event e) {
        if (e.getEventType() == Event.EventType.CacheRead) {
            CacheReadEvent event = (CacheReadEvent) e;

            int address = event.getAddressToReadFrom();

            for (int i = 0; i < this.NumberOfLines; i++) {
                if (address == this.tag_array[i]) {
                    System.out.println("Cache hit");
                    this.LRU_time[i] = Clock.getCurrentTime();
                    Simulator.getEventQueue().addEvent(
                            new CacheResponseEvent(
                                    Clock.getCurrentTime(),
                                    this,
                                    event.getRequestingElement(),
                                    this.cache[i]
                            )
                    );
                    return;
                }
            }

            System.out.println(event.getRequestingElement());

            System.out.println("Cache miss");

            Simulator.getEventQueue().addEvent(
                new MemoryReadEvent(
                    Clock.getCurrentTime() + configuration.Configuration.mainMemoryLatency,
                    this,
                    event.getRequestingElement(), 
                    containingProcessor.getMainMemory(),
                    event.getAddressToReadFrom())
            );

            

        }

        else if (e.getEventType() == Event.EventType.MemoryResponse) {
            MemoryResponseEvent event = (MemoryResponseEvent) e;
            int address = event.getAddressToReadFrom();
            this.cacheWrite(address, event.getValue());
            Simulator.getEventQueue().addEvent(
                        new CacheResponseEvent(
                            Clock.getCurrentTime(),
                            this, 
                            event.getReqestingForElement(),
                            event.getValue())
                    );

            if (event.getReqestingForElement().indentify() == "InstructionFetch") {
                return;
            }
        }

		else if(e.getEventType() == Event.EventType.CacheWrite) {
			CacheWriteEvent event = (CacheWriteEvent) e;

			this.cacheWrite(event.getAddressToWriteTo(), event.getValue());

			MemoryAccess memory_access = (MemoryAccess) event.getRequestingElement();
			memory_access.EX_MA_Latch.setMA_Busy(false);
			// memory_access.EX_MA_Latch.setMA_enable(false);
			memory_access.EX_MA_Latch.setMA_enable(true);
			memory_access.OF_EX_Latch.setEX_Busy(false);
			memory_access.MA_RW_Latch.setRW_enable(true);
			memory_access.getMa_RW_Latch().setInstruction(memory_access.getInstruction());
		}

    }

    @Override
    public String indentify() {
        return "Cache";
    }
}