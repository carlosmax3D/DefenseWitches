package android.support.v4.util;

public class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    public CircularArray() {
        this(8);
    }

    public CircularArray(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        int highestOneBit = Integer.bitCount(i) != 1 ? 1 << (Integer.highestOneBit(i) + 1) : i;
        this.mCapacityBitmask = highestOneBit - 1;
        this.mElements = (Object[]) new Object[highestOneBit];
    }

    private void doubleCapacity() {
        int length = this.mElements.length;
        int i = length - this.mHead;
        int i2 = length << 1;
        if (i2 < 0) {
            throw new RuntimeException("Too big");
        }
        E[] eArr = new Object[i2];
        System.arraycopy(this.mElements, this.mHead, eArr, 0, i);
        System.arraycopy(this.mElements, 0, eArr, i, this.mHead);
        this.mElements = (Object[]) eArr;
        this.mHead = 0;
        this.mTail = length;
        this.mCapacityBitmask = i2 - 1;
    }

    public final void addFirst(E e) {
        this.mHead = (this.mHead - 1) & this.mCapacityBitmask;
        this.mElements[this.mHead] = e;
        if (this.mHead == this.mTail) {
            doubleCapacity();
        }
    }

    public final void addLast(E e) {
        this.mElements[this.mTail] = e;
        this.mTail = (this.mTail + 1) & this.mCapacityBitmask;
        if (this.mTail == this.mHead) {
            doubleCapacity();
        }
    }

    public final E get(int i) {
        if (i < 0 || i >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return this.mElements[(this.mHead + i) & this.mCapacityBitmask];
    }

    public final E getFirst() {
        if (this.mHead != this.mTail) {
            return this.mElements[this.mHead];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final E getLast() {
        if (this.mHead != this.mTail) {
            return this.mElements[(this.mTail - 1) & this.mCapacityBitmask];
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final boolean isEmpty() {
        return this.mHead == this.mTail;
    }

    public final E popFirst() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        E e = this.mElements[this.mHead];
        this.mElements[this.mHead] = null;
        this.mHead = (this.mHead + 1) & this.mCapacityBitmask;
        return e;
    }

    public final E popLast() {
        if (this.mHead == this.mTail) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i = (this.mTail - 1) & this.mCapacityBitmask;
        E e = this.mElements[i];
        this.mElements[i] = null;
        this.mTail = i;
        return e;
    }

    public final int size() {
        return (this.mTail - this.mHead) & this.mCapacityBitmask;
    }
}
