;;  Copyright (c) Chris Turner. All rights reserved.  The use and
;;  distribution terms for this software are covered by the Eclipse Public
;;  License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which can
;;  be found in the file epl-v10.html at the root of this distribution.  By
;;  using this software in any fashion, you are agreeing to be bound by the
;;  terms of this license.  You must not remove this notice, or any other,
;;  from this software.
;;
;;  Enhancement to clojure.test to allow for function specifications

(ns clojure.contrib.spec
  (:require [clojure.test :as test])
  (:use [clojure.contrib.str-utils :only (str-join)]))

(defmacro describe [var-name & args]
  `(let [description# (str-join "\n" (list ~@args))]
     (alter-meta! (var ~var-name) assoc :spec description#)))

(defmacro it
  ([description]
   `(str "- " ~description " (Pending)"))
  ([description assertion]
   `(do
      (test/is ~assertion ~description)
      (str "- " ~description))))
