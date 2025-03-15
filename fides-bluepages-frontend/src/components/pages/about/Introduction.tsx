import React, { FC } from 'react';
import { Trans, useTranslation } from 'react-i18next';

export const Introduction: FC = () => {
    const {t} = useTranslation();

    return (
        <div>
            <div className="flex flex-column">
                <h1 className="font-bold text-xl mt-3 mb-1">{t('screens.introduction.title')}</h1>
                <Trans i18nKey='screens.introduction.intro'>
                    <a className='underline' href="/about/trustedIssuers">Trusted Issuers</a>
                    <a className='underline' href="/api/public/swagger-ui">API interface</a>
                    <a className='underline' href="https://github.com/FIDEScommunity/fides-bluepages">Github repository</a>
                </Trans>
            </div>
        </div>
    );
};
