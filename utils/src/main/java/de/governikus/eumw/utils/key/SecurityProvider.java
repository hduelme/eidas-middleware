/*
 * Copyright (c) 2020 Governikus KG. Licensed under the EUPL, Version 1.2 or as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence"); You may not use this work except
 * in compliance with the Licence. You may obtain a copy of the Licence at:
 * http://joinup.ec.europa.eu/software/page/eupl Unless required by applicable law or agreed to in writing,
 * software distributed under the Licence is distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

package de.governikus.eumw.utils.key;

import java.security.Provider;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


/**
 * this class will provide some security providers
 */
public final class SecurityProvider
{

  /**
   * the bouncy castle provider that is needed for PKCS12 keystores
   */
  static final Provider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

  /**
   * utility class constructor
   */
  private SecurityProvider()
  {
    super();
  }
}
