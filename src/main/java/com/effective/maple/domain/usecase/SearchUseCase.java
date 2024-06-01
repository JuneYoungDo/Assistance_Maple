package com.effective.maple.domain.usecase;

import com.effective.maple.domain.dto.RequestDto.ItemListUpReq;
import com.effective.maple.domain.dto.ResponseDto.ItemInfo;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchUseCase {
    List<ItemInfo> getItemListUp(ItemListUpReq itemListUpReq)
        throws IOException, ParseException, InterruptedException;
}
