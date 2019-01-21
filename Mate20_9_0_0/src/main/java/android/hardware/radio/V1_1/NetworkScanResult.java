package android.hardware.radio.V1_1;

import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.RadioError;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.telephony.AbstractPhoneBase;
import java.util.ArrayList;
import java.util.Objects;

public final class NetworkScanResult {
    public int error;
    public final ArrayList<CellInfo> networkInfos = new ArrayList();
    public int status;

    public final boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || otherObject.getClass() != NetworkScanResult.class) {
            return false;
        }
        NetworkScanResult other = (NetworkScanResult) otherObject;
        if (this.status == other.status && this.error == other.error && HidlSupport.deepEquals(this.networkInfos, other.networkInfos)) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.error))), Integer.valueOf(HidlSupport.deepHashCode(this.networkInfos))});
    }

    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(".status = ");
        builder.append(ScanStatus.toString(this.status));
        builder.append(", .error = ");
        builder.append(RadioError.toString(this.error));
        builder.append(", .networkInfos = ");
        builder.append(this.networkInfos);
        builder.append("}");
        return builder.toString();
    }

    public final void readFromParcel(HwParcel parcel) {
        readEmbeddedFromParcel(parcel, parcel.readBuffer(24), 0);
    }

    public static final ArrayList<NetworkScanResult> readVectorFromParcel(HwParcel parcel) {
        ArrayList<NetworkScanResult> _hidl_vec = new ArrayList();
        HwBlob _hidl_blob = parcel.readBuffer(16);
        int _hidl_vec_size = _hidl_blob.getInt32(8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * 24), _hidl_blob.handle(), 0, true);
        _hidl_vec.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            NetworkScanResult _hidl_vec_element = new NetworkScanResult();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * 24));
            _hidl_vec.add(_hidl_vec_element);
        }
        return _hidl_vec;
    }

    public final void readEmbeddedFromParcel(HwParcel parcel, HwBlob _hidl_blob, long _hidl_offset) {
        HwBlob hwBlob = _hidl_blob;
        this.status = hwBlob.getInt32(_hidl_offset + 0);
        this.error = hwBlob.getInt32(_hidl_offset + 4);
        int _hidl_vec_size = hwBlob.getInt32((_hidl_offset + 8) + 8);
        HwBlob childBlob = parcel.readEmbeddedBuffer((long) (_hidl_vec_size * AbstractPhoneBase.EVENT_ECC_NUM), _hidl_blob.handle(), (_hidl_offset + 8) + 0, true);
        this.networkInfos.clear();
        for (int _hidl_index_0 = 0; _hidl_index_0 < _hidl_vec_size; _hidl_index_0++) {
            CellInfo _hidl_vec_element = new CellInfo();
            _hidl_vec_element.readEmbeddedFromParcel(parcel, childBlob, (long) (_hidl_index_0 * AbstractPhoneBase.EVENT_ECC_NUM));
            this.networkInfos.add(_hidl_vec_element);
        }
        HwParcel hwParcel = parcel;
    }

    public final void writeToParcel(HwParcel parcel) {
        HwBlob _hidl_blob = new HwBlob(24);
        writeEmbeddedToBlob(_hidl_blob, 0);
        parcel.writeBuffer(_hidl_blob);
    }

    public static final void writeVectorToParcel(HwParcel parcel, ArrayList<NetworkScanResult> _hidl_vec) {
        HwBlob _hidl_blob = new HwBlob(16);
        int _hidl_vec_size = _hidl_vec.size();
        _hidl_blob.putInt32(8, _hidl_vec_size);
        int _hidl_index_0 = 0;
        _hidl_blob.putBool(12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * 24);
        while (_hidl_index_0 < _hidl_vec_size) {
            ((NetworkScanResult) _hidl_vec.get(_hidl_index_0)).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_0 * 24));
            _hidl_index_0++;
        }
        _hidl_blob.putBlob(0, childBlob);
        parcel.writeBuffer(_hidl_blob);
    }

    public final void writeEmbeddedToBlob(HwBlob _hidl_blob, long _hidl_offset) {
        _hidl_blob.putInt32(_hidl_offset + 0, this.status);
        _hidl_blob.putInt32(4 + _hidl_offset, this.error);
        int _hidl_vec_size = this.networkInfos.size();
        _hidl_blob.putInt32((_hidl_offset + 8) + 8, _hidl_vec_size);
        int _hidl_index_0 = 0;
        _hidl_blob.putBool((_hidl_offset + 8) + 12, false);
        HwBlob childBlob = new HwBlob(_hidl_vec_size * AbstractPhoneBase.EVENT_ECC_NUM);
        while (true) {
            int _hidl_index_02 = _hidl_index_0;
            if (_hidl_index_02 < _hidl_vec_size) {
                ((CellInfo) this.networkInfos.get(_hidl_index_02)).writeEmbeddedToBlob(childBlob, (long) (_hidl_index_02 * AbstractPhoneBase.EVENT_ECC_NUM));
                _hidl_index_0 = _hidl_index_02 + 1;
            } else {
                _hidl_blob.putBlob((8 + _hidl_offset) + 0, childBlob);
                return;
            }
        }
    }
}