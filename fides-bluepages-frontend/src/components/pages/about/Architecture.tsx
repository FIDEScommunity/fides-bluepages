import React, { FC } from 'react';
import { Trans, useTranslation } from 'react-i18next';
import { Image } from 'primereact/image';

export const Architecture: FC = () => {
    const {t} = useTranslation();

    return (
        <div>
            <div className="flex flex-column">
                <h1 className="font-bold text-xl mt-3 mb-1">{t('screens.architecture.title')}</h1>
                <p>{t('screens.architecture.subTitle')}</p>
                <Image src="/architecture.png" alt="Architecture" width="100%"/>
                <Trans i18nKey='screens.architecture.description'></Trans>
            </div>
        </div>
    );
};
