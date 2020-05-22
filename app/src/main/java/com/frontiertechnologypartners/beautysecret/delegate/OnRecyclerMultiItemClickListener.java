package com.frontiertechnologypartners.beautysecret.delegate;

public interface OnRecyclerMultiItemClickListener extends BaseRecyclerListener {
    void onItemClick(int position);
    void onClickAction(int position);
}