package com.effective.maple.domain.usecase;

import com.effective.maple.domain.dto.CompareDto.ItemInformation;
import com.effective.maple.domain.dto.ResponseDto.ItemInfo;
import java.io.IOException;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface SearchUseCase {
    List<ItemInfo> getItemListUp(ItemInformation itemInformation)
        throws IOException, ParseException, InterruptedException;
}
