package vendor.huawei.hardware.radio.V2_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RspMsgPayload {
    public int nData;
    public final ArrayList<Integer> nDatas = new ArrayList();
    public String strData = new String();
    public final ArrayList<String> strDatas = new ArrayList();

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != RspMsgPayload.class) {
            return false;
        }
        RspMsgPayload other = (RspMsgPayload) otherObject;
        if (this.nData == other.nData && HidlSupport.deepEquals(this.nDatas, other.nDatas) && HidlSupport.deepEquals(this.strData, other.strData) && HidlSupport.deepEquals(this.strDatas, other.strDatas)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.nData))), Integer.valueOf(HidlSupport.deepHashCode(this.nDatas)), Integer.valueOf(HidlSupport.deepHashCode(this.strData)), Integer.valueOf(HidlSupport.deepHashCode(this.strDatas))});
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(".nData = ");
        builder.append(this.nData);
        builder.append(", .nDatas = ");
        builder.append(this.nDatas);
        builder.append(", .strData = ");
        builder.append(this.strData);
        builder.append(", .strDatas = ");
        builder.append(this.strDatas);
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(56), 0);
    }

    public static final ArrayList<RspMsgPayload> readVectorFromParcel(HwParcel parcel) {
        ArrayList<RspMsgPayload> _hidl_vec = new ArrayList();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 56), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            RspMsgPayload _hidl_vec_element = new RspMsgPayload();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 56));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.nData = hwBlob.getInt32(_hidl_offset + 0);
        int _hidl_vec_size = hwBlob.getInt32((_hidl_offset + 8) + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 4), _hidl_blob.handle(), (_hidl_offset + 8) + 0, true);
        this.nDatas.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            this.nDatas.add(Integer.valueOf(childBlob.getInt32((long) (_hidl_index_0 * 4))));
        }
        this.strData = hwBlob.getString(_hidl_offset + 24);
        parcel.readEmbeddedBuffer((long) (this.strData.getBytes().length + 1), _hidl_blob.handle(), (_hidl_offset + 24) + 0, false);
        int _hidl_vec_size2 = hwBlob.getInt32((_hidl_offset + 40) + 8);
        HwBlob childBlob2 = parcel.readEmbeddedBuffer((long) (_hidl_vec_size2 * 16), _hidl_blob.handle(), (_hidl_offset + 40) + 0, true);
        this.strDatas.clear();
        for (int _hidl_index_02 = 0; _hidl_index_02 < _hidl_vec_size2; _hidl_index_02++) {
            String _hidl_vec_element = new String();
            _hidl_vec_element = childBlob2.getString((long) (_hidl_index_02 * 16));
            parcel.readEmbeddedBuffer((long) (_hidl_vec_element.getBytes().length + 1), childBlob2.handle(), (long) ((_hidl_index_02 * 16) + 0), false);
            this.strDatas.add(_hidl_vec_element);
        }
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(56);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<RspMsgPayload> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        int _hidl_index_0 = 0;
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 56);
        while (_hidl_index_0 < _hidl_vec_size) {
            ((RspMsgPayload) _hidl_vec.get(_hidl_index_0)).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 56));
            _hidl_index_0++;
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        hwBlob.putInt32(_hidl_offset + 0, this.nData);
        int _hidl_vec_size = this.nDatas.size();
        hwBlob.putInt32((_hidl_offset + 8) + 8, _hidl_vec_size);
        hwBlob.putBool((_hidl_offset + 8) + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 4);
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            childBlob.putInt32((long) (_hidl_index_0 * 4), ((Integer) this.nDatas.get(_hidl_index_0)).intValue());
        }
        hwBlob.putBlob((_hidl_offset + 8) + 0, childBlob);
        hwBlob.putString(_hidl_offset + 24, this.strData);
        _hidl_vec_size = this.strDatas.size();
        hwBlob.putInt32((_hidl_offset + 40) + 8, _hidl_vec_size);
        int _hidl_index_02 = 0;
        hwBlob.putBool((_hidl_offset + 40) + 12, false);
        HwBlob childBlob2 = new HwBlob(_hidl_vec_size * 16);
        while (true) {
            int _hidl_index_03 = _hidl_index_02;
            if (_hidl_index_03 < _hidl_vec_size) {
                childBlob2.putString((long) (_hidl_index_03 * 16), (String) this.strDatas.get(_hidl_index_03));
                _hidl_index_02 = _hidl_index_03 + 1;
            } else {
                hwBlob.putBlob((_hidl_offset + 40) + 0, childBlob2);
                return;
            }
        }
    }
}
