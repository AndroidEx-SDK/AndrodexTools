package com.androidex.lockcontrolapp;

import android.content.Context;
import android.os.RemoteException;

import com.androidex.IDoorLockInterface;
import com.androidex.plugins.kkfile;

/**
 * Created by Administrator on 2018/4/2.
 */

public class DoorLockServiceBinder extends IDoorLockInterface.Stub {
    private Context context;
    public DoorLockServiceBinder(Context context){
        this.context = context;
    }

    String rkeyDev = "/dev/rkey";
    int ident = 0;

    /**
     * 开门指令
     *
     * @param index 门的序号,主门=0,副门=1
     * @param delay 延迟关门的时间,0表示不启用延迟关门,大于0表示延迟时间,延迟时间为delay*150ms
     * @return 大于0表示成功, 实际上等于9表示真正的成功, 因为返回值表示写入的数据, 开门指令长度为9.
     */
    public int openDoor(int index, int delay) {
        kkfile rkey = new kkfile();
        if (index < 0 || index > 0xFE) index = 0;
        if (ident < 0 || ident > 0xFE) ident = 0;
        if (delay < 0 || delay > 0xFE) delay = 0;
        String cmd = String.format("FB%02X2503%02X01%02X00FE", ident, index, delay);
        int r = rkey.native_file_writeHex(rkeyDev, cmd);

        if (r > 0) {
            SoundPoolUtil.getSoundPoolUtil().loadVoice(context, 011111);
        }
        return r > 0 ? 1 : 0;
    }

    public int closeDoor(int index) {
        kkfile rkey = new kkfile();

        if (index < 0 || index > 0xFE) index = 0;
        if (ident < 0 || ident > 0xFE) ident = 0;
        String cmd = String.format("FB%02X2503%02X000000FE", ident, index);
        int r = rkey.native_file_writeHex(rkeyDev, cmd);
        return r > 0 ? 1 : 0;
    }
}
