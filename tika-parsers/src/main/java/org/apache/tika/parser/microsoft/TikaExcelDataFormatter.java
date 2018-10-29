/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.tika.parser.microsoft;

import java.util.Locale;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.LocaleUtil;

/**
 * Overrides Excel's General format to include more
 * significant digits than the MS Spec allows.
 * See TIKA-2025.
 */
public class TikaExcelDataFormatter extends DataFormatter {

    private OfficeParserConfig officeParserConfig;

    public TikaExcelDataFormatter(OfficeParserConfig officeParserConfig) {
        this(officeParserConfig, LocaleUtil.getUserLocale());
    }

    public TikaExcelDataFormatter (OfficeParserConfig officeParserConfig, Locale locale) {
        super(locale);
        this.officeParserConfig = officeParserConfig;
        addFormat("General", new TikaExcelGeneralFormat(locale));
        addFormat("general", new TikaExcelGeneralFormat(locale));
    }

    @Override
    public String formatRawCellContents(double value, int formatIndex, String formatString) {
        if(officeParserConfig.getExtractFormattedValues()) {
            return super.formatRawCellContents(value, formatIndex, formatString);
        }
        else {
            return String.valueOf(value);
        }
    }

    @Override
    public String formatRawCellContents(double value, int formatIndex, String formatString, boolean use1904Windowing) {
        if(officeParserConfig.getExtractFormattedValues()) {
            return super.formatRawCellContents(value, formatIndex, formatString, use1904Windowing);
        } else {
            return String.valueOf(value);
        }
    }
}
