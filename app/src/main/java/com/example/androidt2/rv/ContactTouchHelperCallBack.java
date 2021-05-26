package com.example.androidt2.rv;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidt2.AddActivity;
import com.example.androidt2.MainActivity;
import com.example.androidt2.R;

public class ContactTouchHelperCallBack extends ItemTouchHelper.SimpleCallback {

    Context context;
    Drawable background;
    Drawable xMark;
    int xMarkMargin;
    boolean initiated;

    public ContactTouchHelperCallBack(Context context, int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
        this.context = context;
    }

    private void init() {
        background = new ColorDrawable(Color.RED);
        xMark = ContextCompat.getDrawable(context, R.drawable.ic_clear_24);
        xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        initiated = true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
        // TODO: 23/05/2021 fazer deletar
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;

        if (viewHolder.getAdapterPosition() == -1) {
            return;
        }

        if (!initiated) {
            init();
        }
/*
        // draw red background
        background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        background.draw(c);

        // draw x mark
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = xMark.getIntrinsicWidth();
        int intrinsicHeight = xMark.getIntrinsicWidth();

        int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
        int xMarkRight = itemView.getRight() - xMarkMargin;
        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
        int xMarkBottom = xMarkTop + intrinsicHeight;
        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

        xMark.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);*/
        background.setBounds(itemView.getLeft() + (int) dX/4, itemView.getTop(), itemView.getLeft(), itemView.getBottom());
        background.draw(c);

        // draw x mark
        int itemHeight = itemView.getBottom() - itemView.getTop();
        int intrinsicWidth = xMark.getIntrinsicWidth();
        int intrinsicHeight = xMark.getIntrinsicWidth();

        int xMarkLeft = itemView.getLeft() - xMarkMargin;
        int xMarkRight = itemView.getLeft() - xMarkMargin - intrinsicWidth;
        int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
        int xMarkBottom = xMarkTop + intrinsicHeight;
        xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

        xMark.draw(c);

        if (getSwipeDirs(recyclerView, viewHolder) == ItemTouchHelper.RIGHT) { //Verifica a direção
            //Se é para a direita, vai apena 75% do espaço
            super.onChildDraw(c, recyclerView, viewHolder, dX/4, dY, actionState, isCurrentlyActive);
        }
        else{//Para a esquerda
            //Vai completamente para a esquerda
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            Intent intent = new Intent(context, AddActivity.class);
            int id = viewHolder.getAdapterPosition(); //Captura o ID
            intent.putExtra("key", id);
            context.startActivity(intent);
        }
    }
}
