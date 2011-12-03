/*
 * pcp - The Producer of C++ Programs
 * Copyright (C) 2011 Nabil Hassein, Thomas Huston, Mike Morreale, Marta Wilgan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pcp.translator;

import java.util.Map;

/**
 * An interface for classes that maintain a mapping
 * of variables within their scope.
 *
 * @author Nabil Hassein
 * @author Thomas Huston
 * @author Mike Morreale
 * @author Marta Wilgan
 * 
 * @version 1.4
 */
public interface JavaScope {

  /**
   * Adds a variable to the scope.
   *
   * @param name The name of the variable.
   * @param type The type of the variable.
   */
  public void addVariable(String name, JavaType type);

  /**
   * Gets the parent scope.
   *
   * @return The parent scope.
   */
  public JavaScope getParentScope();

  /**
   * Gets the scope in which the specified variable is declared.
   *
   * @param name The name of the variable.
   *
   * @return The scope of the variable if it exists;
   * <code>null</code> otherwise.
   */
  public JavaScope getVariableScope(String name);
  
  /**
   * Gets the type of the specified variable.
   *
   * @param name The name of the variable.
   *
   * @return The type if the variable exists;
   * <code>null</code> otherwise.
   */
  public JavaType getVariableType(String name);
  
  /**
   * Checks if the current scope is of the specified type.
   *
   * @param type The type of the scope.
   *
   * @return <code>True</code> if the scope is of the specified
   * type; <code>false</code> otherwise.
   */
  public boolean hasName(String name);
  
  /**
   * Checks if a variable is currently in scope.
   *
   * @return <code>True</code> if the variable is in scope;
   * <code>false</code> otherwise.
   */
  public boolean isInScope(String name);
  
  /**
   * Checks if the specified variable is static.
   *
   * @return <code>True</code> if the variable is static;
   * <code>false</code> otherwise.
   */
  public boolean isVariableStatic(String name);

}
