package com.google.android.apps.gsa.search.shared.service.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.ExtendableMessageNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import java.io.IOException;

public final class ClientEventProto extends ExtendableMessageNano<ClientEventProto> implements Cloneable {
    private static volatile ClientEventProto[] _emptyArray;
    private int bitField0_;
    private int eventId_;

    public ClientEventProto() {
        clear();
    }

    public static ClientEventProto[] emptyArray() {
        if (_emptyArray == null) {
            synchronized (InternalNano.LAZY_INIT_LOCK) {
                if (_emptyArray == null) {
                    _emptyArray = new ClientEventProto[0];
                }
            }
        }
        return _emptyArray;
    }

    public static ClientEventProto parseFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        return new ClientEventProto().mergeFrom(codedInputByteBufferNano);
    }

    public static ClientEventProto parseFrom(byte[] bArr) throws InvalidProtocolBufferNanoException {
        ClientEventProto clientEventProto = new ClientEventProto();
        MessageNano.mergeFrom(clientEventProto, bArr);
        return clientEventProto;
    }

    public ClientEventProto clear() {
        this.bitField0_ = 0;
        this.eventId_ = 0;
        this.unknownFieldData = null;
        this.cachedSize = -1;
        return this;
    }

    public ClientEventProto clearEventId() {
        this.eventId_ = 0;
        this.bitField0_ &= -2;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano, com.google.protobuf.nano.ExtendableMessageNano, com.google.protobuf.nano.ExtendableMessageNano, java.lang.Object
    public ClientEventProto clone() {
        try {
            return (ClientEventProto) super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError(e2);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
    public int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        return (this.bitField0_ & 1) != 0 ? computeSerializedSize + CodedOutputByteBufferNano.computeInt32Size(1, this.eventId_) : computeSerializedSize;
    }

    public int getEventId() {
        return this.eventId_;
    }

    public boolean hasEventId() {
        return (this.bitField0_ & 1) != 0;
    }

    @Override // com.google.protobuf.nano.MessageNano
    public ClientEventProto mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException {
        while (true) {
            int readTag = codedInputByteBufferNano.readTag();
            if (readTag == 0) {
                return this;
            }
            if (readTag == 8) {
                this.eventId_ = codedInputByteBufferNano.readInt32();
                this.bitField0_ |= 1;
            } else if (!super.storeUnknownField(codedInputByteBufferNano, readTag)) {
                return this;
            }
        }
    }

    public ClientEventProto setEventId(int i) {
        this.bitField0_ |= 1;
        this.eventId_ = i;
        return this;
    }

    @Override // com.google.protobuf.nano.MessageNano, com.google.protobuf.nano.ExtendableMessageNano
    public void writeTo(CodedOutputByteBufferNano codedOutputByteBufferNano) throws IOException {
        if ((this.bitField0_ & 1) != 0) {
            codedOutputByteBufferNano.writeInt32(1, this.eventId_);
        }
        super.writeTo(codedOutputByteBufferNano);
    }
}
