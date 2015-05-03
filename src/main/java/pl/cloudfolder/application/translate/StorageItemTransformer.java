package pl.cloudfolder.application.translate;

import org.springframework.stereotype.Component;
import pl.cloudfolder.application.dto.StorageItemDto;
import pl.cloudfolder.domain.clients.AppClient;
import pl.cloudfolder.domain.storage.StorageItem;

/**
 * Created by Adiki on 2015-05-03.
 */
@Component
public class StorageItemTransformer {
    public StorageItemDto apply(StorageItem storageItem, AppClient appClient) {
        return new StorageItemDto(storageItem.id,
                appClient.id(),
                storageItem.name,
                storageItem.isDirectory());
    }
}
