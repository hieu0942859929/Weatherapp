package com.example.weatherapp;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DiaLogTemp extends AppCompatDialogFragment {
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_temp, null);

        builder.setView(view).setTitle(" ").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        ImageButton btnTemp = view.findViewById(R.id.btnTemp);
        ImageButton btnHumidity = view.findViewById(R.id.btnHumidity);
        ImageButton btnRain = view.findViewById(R.id.btnRain);
        ImageButton btnWind = view.findViewById(R.id.btnWind);

        final TextView tvDiaLog = view.findViewById(R.id.tvDiaLog);
        tvDiaLog.setText("  Nhiệt độ cao làm cơ thể mất nhiều năng lượng hơn để hạ nhiệt. Bạn cũng sẽ mất nước nhanh hơn. Nhiệt độ thấp hơn có thể chuyển đổi nguồn năng lượng cơ thể bạn từ tiêu thụ ít chất béo hơn trở thành tiêu thụ carbohydrate nhiều hơn");

        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDiaLog.setText("  Nhiệt độ cao làm cơ thể mất nhiều năng lượng hơn để hạ nhiệt. Bạn cũng sẽ mất nước nhanh hơn. Nhiệt độ thấp hơn có thể chuyển đổi nguồn năng lượng cơ thể bạn từ tiêu thụ ít chất béo hơn trở thành tiêu thụ carbohydrate nhiều hơn");
            }
        });
        btnHumidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDiaLog.setText("  Độ ẩm cao ngăn không cho mồ hôi thoát ra. Điều đó có nghĩa là tim và phổi của bạn phải làm việc nhiều hơn để kiểm soát hoạt động hạ nhiệt của cơ thể. Hãy cẩn trọng với tình trạng mất nước");
            }
        });
        btnRain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDiaLog.setText("  Khi chạy bộ dưới trời mưa, da của bạn rất dễ bị phồng rộp (chafing), nhất là những chỗ hay bị chà xát với quần, áo, giày chạy bộ. Trong khi việc chạy dưới mưa hoàn toàn an toàn, việc chạy dưới trời sấm chớp lại là một việc khác. Chẳng ai muốn bị sét đánh khi đang chạy cả");
            }
        });
        btnWind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDiaLog.setText("  Gió làm mát có thể giúp bạn duy trì thân nhiệt bằng cách tăng tiết mồ hôi. Ngoài ra, gió ngược chiều với tốc độ 10 dặm/ giờ có thể làm tốc độ của bạn chậm lại 8% còn gió xuôi với vận tốc tương tự có thể tăng tốc của bạn lên 5%");
            }
        });
        return builder.create();
    }
}
