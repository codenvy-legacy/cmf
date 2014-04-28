/*
 * Copyright 2014 Codenvy, S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

grammar Editor;

import Common;

editor                  :
                            'Toolbar' COLON
                            BEGIN
                                toolbar
                            END
                            'Panel' COLON
                            BEGIN
                                pPanel
                            END
                            'Workspace' COLON
                            BEGIN
                                workspace
                            END
                        ;

toolbar                 :
                            'alignment' COLON tbAlignment

                            'Group'     COLON
                            BEGIN
                                tbGroup
                            END

                            'Size'      COLON
                            BEGIN
                                tbSize
                            END

                            'Item'      COLON
                            BEGIN
                                tbItem
                            END


                        ;

tbGroup                 :
                            'margin'    COLON gMargin
                        ;

tbSize                  :
                            'compact'   COLON compact
                            'full'      COLON full
                        ;

compact                 :
                            INTEGER
                        ;

full                    :
                            INTEGER
                        ;

tbItem                  :
                            'size'      COLON iSize
                            'margin'    COLON iMargin
                            'alignment' COLON iAlignment

                            'Text'      COLON
                            BEGIN
                                iText
                            END
                        ;

iText                   :
                            'alignment' COLON iTextAlignment
                        ;

tbAlignment             :
                            SIDE
                        ;

cvSize                  :
                            INTEGER
                        ;

wvSize                  :
                            INTEGER
                        ;

iSize                   :
                            INTEGER
                        ;

iMargin                 :
                            TEXT
                        ;

gMargin                 :
                            TEXT
                        ;

iAlignment              :
                            ALIGNMENT
                        ;

iTextAlignment          :
                            ALIGNMENT
                        ;

pPanel                  :
                            'alignment'     COLON ppAlignment
                            'default size'  COLON ppDefaultSize
                        ;

ppAlignment             :
                            SIDE
                        ;

ppDefaultSize           :
                            INTEGER
                        ;

workspace               :
                            'scrollability' COLON scrollability
                        ;

scrollability           :
                            SCROLLABILITY
                        ;

SIDE                    :
                            'SOUTH' |
                            'NORTH' |
                            'WEST'  |
                            'EAST'
                        ;
ALIGNMENT               :
                            'TOP'   |
                            'BOTTOM'|
                            'LEFT'  |
                            'RIGHT' |
                            'CENTER'
                        ;
SCROLLABILITY           :
                            'YES'   |
                            'NO'
                        ;