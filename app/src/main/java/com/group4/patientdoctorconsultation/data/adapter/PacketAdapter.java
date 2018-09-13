package com.group4.patientdoctorconsultation.data.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.group4.patientdoctorconsultation.R;
import com.group4.patientdoctorconsultation.common.BindingAdapter;
import com.group4.patientdoctorconsultation.common.ClickListener;
import com.group4.patientdoctorconsultation.data.model.DataPacket;
import com.group4.patientdoctorconsultation.databinding.ItemDataPacketBinding;

public class PacketAdapter extends BindingAdapter<DataPacket, ItemDataPacketBinding> {

    public PacketAdapter(ClickListener<DataPacket> clickListener){
        super(clickListener);
    }

    @Override
    protected ItemDataPacketBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemDataPacketBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_data_packet, parent, false);
        binding.getRoot().setOnClickListener(v -> clickListener.onClicked(binding.getDataPacket()));
        return binding;
    }

    @Override
    protected void bind(ItemDataPacketBinding binding, DataPacket dataPacket) {
        binding.setDataPacket(dataPacket);
    }

}
