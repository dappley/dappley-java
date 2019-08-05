package com.dappley.android.bean;

import android.support.annotation.NonNull;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Receiver implements Comparable<Receiver> {
    private String address;
    private Date latestTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receiver receiver = (Receiver) o;

        return address != null ? address.equals(receiver.address) : receiver.address == null;
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public int compareTo(@NonNull Receiver o) {
        return o.getLatestTime().compareTo(this.getLatestTime());
    }
}
