;;  Copyright (c) Chris Turner. All rights reserved.  The use and
;;  distribution terms for this software are covered by the Eclipse Public
;;  License 1.0 (http://opensource.org/licenses/eclipse-1.0.php) which can
;;  be found in the file epl-v10.html at the root of this distribution.  By
;;  using this software in any fashion, you are agreeing to be bound by the
;;  terms of this license.  You must not remove this notice, or any other,
;;  from this software.

(ns clojure.contrib.test-contrib.test-spec
  (:use clojure.test
	[clojure.contrib.spec :only (describe it)]))

(defn- example-fn [arg1 arg2] (str arg1 arg2))

(deftest test-describe-enhances-metadata
  (is (= (do
           (describe example-fn)
           (-> #'example-fn meta :spec))
         ""))
  (is (= (do
           (describe example-fn
             (it "is composed of two strings"
                 (= (example-fn "a" "b") "ab")))
           (-> #'example-fn meta :spec))
         "- is composed of two strings")))
