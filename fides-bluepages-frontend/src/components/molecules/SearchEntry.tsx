import React, { CSSProperties, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { IconField } from 'primereact/iconfield';
import { InputIcon } from 'primereact/inputicon';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';


export interface SearchEntryProps {
    onSearch: (value: string | undefined) => void;
    placeHolder?: string;
    style?: CSSProperties | undefined;
    className?: string;
}

export const SearchEntry: React.FC<SearchEntryProps> = (props) => {
    const [searchValue, setSearchValue] = useState<string>("");
    const {t} = useTranslation();

    function setValue(value: string) {
        setSearchValue(value);
    }

    return (
        <IconField iconPosition="right" className={props.className} style={props.style}>
            <InputIcon style={{top: 15}}>
                <Button label="Search" style={{width: '90px'}} onClick={() => {
                    console.log(searchValue);
                    props.onSearch(searchValue);
                }}/>
            </InputIcon>
            <InputText autoFocus className="w-full" style={{height: '57px', paddingLeft: '20px', paddingRight: '110px'}}
                       type="text"
                       value={searchValue}
                       onChange={(e) => setValue(e.target.value)}
                       onKeyUp={(e) => {
                           if (e.key === 'Enter') {
                               props.onSearch(searchValue);
                           }
                       }}
                       placeholder={(props.placeHolder === undefined) ? t('generic.startSearching') : props.placeHolder}
            />
        </IconField>
    );
};

