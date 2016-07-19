Do(function () {
    var cookieCfg = {key: "ap", cookie: "1"}, $doubanTip = $("#doubanapp-tip"), data = {};

    function hideDoubanTip() {
        if (!$doubanTip.length) {
            return
        }
        $doubanTip.hide();
        data[cookieCfg.key] = cookieCfg.cookie;
        set_cookie(data)
    }

    $doubanTip.delegate("a", "click", hideDoubanTip);
    if (!get_cookie(cookieCfg.key)) {
        $doubanTip.show()
    }
    var popup;
    var nav = $("#db-global-nav");
    var more = nav.find(".bn-more");

    function handleShowMoreActive(c) {
        var a = $(c.currentTarget);
        var b = a.parent();
        hideDoubanTip();
        if (popup) {
            popup.parent().removeClass("more-active");
            if ($.contains(b[0], popup[0])) {
                popup = null;
                return
            }
        }
        b.addClass("more-active");
        popup = b.find(".more-items");
        popup.trigger("moreitem:show")
    }

    nav.delegate(".bn-more, .top-nav-reminder .lnk-remind", "click", function (a) {
        a.preventDefault();
        handleShowMoreActive(a);
        return
    }).delegate(".lnk-doubanapp", "mouseenter", function (b) {
        var a = $(this);
        if (!a.parent().hasClass("more-active")) {
            handleShowMoreActive(b)
        }
    }).delegate(".top-nav-doubanapp", "mouseleave", function () {
        var b = $(this);
        var a = b.find(".lnk-doubanapp");
        if (popup) {
            b.removeClass("more-active");
            if ($.contains(b[0], popup[0])) {
                popup = null
            }
        }
    });
    $(document).click(function (a) {
        if ($(a.target).closest(".more-items").length || $(a.target).closest(".more-active").length) {
            return
        }
        if (popup) {
            popup.parent().removeClass("more-active");
            popup = null
        }
    });
});

!function (t) {
    function i(s) {
        if (e[s])return e[s].exports;
        var a = e[s] = {exports: {}, id: s, loaded: !1};
        return t[s].call(a.exports, a, a.exports, i), a.loaded = !0, a.exports
    }

    var e = {};
    return i.m = t, i.c = e, i.p = "", i(0)
}([function (t, i) {
    "use strict";
    function e(t, i) {
        if (!(t instanceof i))throw new TypeError("Cannot call a class as a function")
    }

    var s = function () {
        function t(t, i) {
            for (var e = 0; e < i.length; e++) {
                var s = i[e];
                s.enumerable = s.enumerable || !1, s.configurable = !0, "value" in s && (s.writable = !0), Object.defineProperty(t, s.key, s)
            }
        }

        return function (i, e, s) {
            return e && t(i.prototype, e), s && t(i, s), i
        }
    }(), a = "prev", n = "next", r = function () {
        function t(i) {
            var s = arguments.length <= 1 || void 0 === arguments[1] ? ".sub-box" : arguments[1], a = arguments.length <= 2 || void 0 === arguments[2] ? ".sub" : arguments[2];
            e(this, t), this.target = $(i), this.main = this.target.find(s), this.split = a, this.index = 0, this.wrapperWidth = this.main.parent().width(), this.style = {}, this.initWidth(), this.addButton()
        }

        return s(t, [{
            key: "initWidth", value: function () {
                var t = 0;
                this.main.find(this.split).each(function (i) {
                    var e = $(this).outerWidth();
                    t += e
                }), this.main.width(t), this.style.width = t, this.boundary = {
                    prev: 0,
                    next: 0 - Math.floor(t / this.wrapperWidth) * this.wrapperWidth
                }
            }
        }, {
            key: "addButton", value: function () {
                if (this.style.width > this.wrapperWidth) {
                    var t = '<a class="category-button prev" dist=' + a + '><i class="fa fa-arrow-circle-left"></i></a>', i = '<a class="category-button next" dist=' + n + '><i class="fa fa-arrow-circle-right"></i></a>';
                    this.target.append(t).append(i), this.bindEvnet()
                }
            }
        }, {
            key: "bindEvnet", value: function () {
                var t = this;
                this.target.delegate(".category-button", "click", function (i) {
                    var e = $(this), s = e.attr("dist") || n;
                    t.slide(s)
                })
            }
        }, {
            key: "slide", value: function (t) {
                var i = (this.index, parseInt(this.main.css("left"), 10) || 0), e = void 0;
                t === a ? e = i + this.wrapperWidth : t === n && (e = i - this.wrapperWidth), e <= this.boundary.prev && e >= this.boundary.next && (this.main.css({left: e}), 0 !== e ? e === this.boundary.next && (this.target.addClass("slide"), this.target.addClass("last")) : this.target.removeClass("slide").removeClass("last"))
            }
        }]), t
    }(), h = function () {
        function t() {
            var i = arguments.length <= 0 || void 0 === arguments[0] ? ".categories .item" : arguments[0], s = arguments.length <= 1 || void 0 === arguments[1] ? "hover" : arguments[1], a = arguments.length <= 2 || void 0 === arguments[2] ? 120 : arguments[2], n = arguments.length <= 3 || void 0 === arguments[3] ? "body" : arguments[3];
            e(this, t), this.target = i, this.cls = s, this.time = a, this.delegator = $(n), this.bindEvnet()
        }

        return s(t, [{
            key: "bindEvnet", value: function () {
                var t = this, i = this.cls, e = this.clearTimer, s = this.startTimer;
                this.delegator.delegate(this.target, "mouseover", function () {
                    e.call(t);
                    var a = $(this);
                    s.call(t, function () {
                        a.siblings().removeClass(i), a.addClass(i)
                    })
                }), this.delegator.delegate(this.target, "mouseout", function () {
                    e.call(t);
                    var a = $(this);
                    s.call(t, function () {
                        a.removeClass(i)
                    })
                })
            }
        }, {
            key: "clearTimer", value: function () {
                this.timer && (clearTimeout(this.timer), this.timer = null)
            }
        }, {
            key: "startTimer", value: function (t) {
                var i = this;
                this.timer || (this.timer = setTimeout(function () {
                    t(), i.clearTimer()
                }, this.time))
            }
        }]), t
    }();
    !function () {
        var t = $(".sub-category");
        t.each(function () {
            new r(this)
        });
        new h
    }()
}]);


$(function () {
    var k = document.body.clientHeight || window.screen.availHeight;
    var c = null;
    var i = $("#market-header");
    var h = i.outerHeight(true);
    var a = i.next();
    var f = i.find(".logo");
    var b = f.attr("href");
    var d = $("#db-global-nav");
    var g = (d.height() || 0) + (i.height() || 0);
    var e = $("<div>");
    var j = 0;
    e.css({height: h});
    $(window).bind("scroll", function (l) {
        if (!c) {
            c = setTimeout(function () {
                var m = window.scrollY;
                if (m > g && j > 0) {
                    i.addClass("fixed");
                    e.insertAfter(i);
                    f.attr({href: "/"})
                } else {
                    i.removeClass("fixed");
                    e.remove();
                    f.attr({href: b})
                }
                clearTimeout(c);
                c = null;
                j = 1
            }, 100)
        }
    })
});
!function (t) {
    function i(n) {
        if (e[n])return e[n].exports;
        var s = e[n] = {exports: {}, id: n, loaded: !1};
        return t[n].call(s.exports, s, s.exports, i), s.loaded = !0, s.exports
    }

    var e = {};
    return i.m = t, i.c = e, i.p = "", i(0)
}({
    0: function (t, i, e) {
        "use strict";
        function n(t) {
            return t && t.__esModule ? t : {"default": t}
        }

        var s = e(336), r = n(s);
        !function () {
            function t(t, i) {
                var e = i.find("a")[0].href, n = i.find("img")[0].src;
                $.post("/api/stat/home_top_banner", {index: t, jump_url: e, img_src: n}).done(function (t) {
                    t.r && console.error(t)
                })
            }

            $("#slide-top .slide").each(function (i, e) {
                var n = new r["default"]({
                    target: this, afterSlide: function (i) {
                        var e = 100, n = 20, s = $(window), r = s.height(), a = s.scrollTop(), o = this.target.parent().position().top, l = this.target.height();
                        if (o + e > a && a > o + l - r - n) {
                            var h = this.target.find(".slide-item:nth-child(" + (i + 1) + ")");
                            t(i, h)
                        }
                    }
                });
                t(0, n.target.find(".slide-item:nth-child(1)"))
            })
        }()
    }, 336: function (t, i) {
        "use strict";
        function e(t, i) {
            if (!(t instanceof i))throw new TypeError("Cannot call a class as a function")
        }

        Object.defineProperty(i, "__esModule", {value: !0});
        var n = function () {
            function t(t, i) {
                for (var e = 0; e < i.length; e++) {
                    var n = i[e];
                    n.enumerable = n.enumerable || !1, n.configurable = !0, "value" in n && (n.writable = !0), Object.defineProperty(t, n.key, n)
                }
            }

            return function (i, e, n) {
                return e && t(i.prototype, e), n && t(i, n), i
            }
        }(), s = {
            target: ".slide",
            slideItem: ".slide-inner",
            childrenItem: ".slide-item",
            imageItem: ".slide-main-image",
            setHeight: !1,
            style: null,
            beforeSlide: null,
            afterSlide: null,
            paginator: !0,
            button: !0,
            auto: !0,
            interval: 4500,
            animateTime: 500,
            startIndex: 0,
            paginatorClass: "slide-paginator-item",
            nextButtonClass: "slide-button-next",
            prevButtonClass: "slide-button-prev"
        }, r = function () {
            function t(i) {
                e(this, t);
                var n = Object.assign({}, s, i);
                this.config = n, this.init()
            }

            return n(t, [{
                key: "init", value: function () {
                    var t = $(this.config.target), i = t.find(this.config.slideItem), e = t.find(this.config.childrenItem);
                    return this.target = t, this.slideItem = i, this.index = parseInt(this.config.startIndex, 10), this.pending = !1, this.initStyle(), this.setHeight(), this.setStyle(), e.length > 1 && (this.renderPaginator(), this.renderButton(), this.bindEvent(), this.startTimer()), this
                }
            }, {
                key: "initStyle", value: function () {
                    var t = {position: "relative", zIndex: 1}, i = Object.assign({}, {
                        position: "absolute",
                        zIndex: 1
                    }, this.config.style);
                    return this.target.css(t), this.slideItem.css(i), this
                }
            }, {
                key: "setHeight", value: function () {
                    var t = this;
                    if (this.config.style && this.config.style.height)return this;
                    var i = this.target.find(this.config.imageItem).first(), e = i.height();
                    return this.target.css({height: e}), 0 >= e && i.on("load", function () {
                        var i = $(t).height();
                        t.target.css({height: i})
                    }), this
                }
            }, {
                key: "setStyle", value: function () {
                    var t = this.target.outerWidth(), i = this.target.find(this.config.childrenItem), e = i.length, n = this.index, s = t * e, r = Object.assign({}, {
                        width: s,
                        top: 0,
                        left: 0 - s * n
                    }, this.config.style);
                    return this.slideItem.css(r), this.bound = [0, s - t], this
                }
            }, {
                key: "renderPaginator", value: function () {
                    if (!this.config.paginator)return null;
                    this.paginator && this.paginator.remove();
                    for (var t = this.target.find(this.config.childrenItem), i = t.length, e = '<ul class="slide-paginator">', n = 0; i > n; n++) {
                        var s = n === this.index ? "active" : "";
                        e += '<li class="' + this.config.paginatorClass + " " + s + '" data-index="' + n + '">●</li>'
                    }
                    return e += "</ul>", this.paginator = $(e), this.target.append(this.paginator), this
                }
            }, {
                key: "renderButton", value: function () {
                    if (!this.config.button)return null;
                    var t = '<a class="' + this.config.nextButtonClass + '"></a>', i = '<a class="' + this.config.prevButtonClass + '"></a>';
                    return this.target.append(t), this.target.append(i), this
                }
            }, {
                key: "bindEvent", value: function () {
                    var t = this, i = "." + this.config.paginatorClass, e = "." + this.config.nextButtonClass, n = "." + this.config.prevButtonClass;
                    return this.target.delegate(i, "click", function (i) {
                        i.preventDefault();
                        var e = $(this).attr("data-index") || $(this).index();
                        t.slide(e)
                    }), this.target.delegate(e, "click", function (i) {
                        i.preventDefault(), t.next()
                    }), this.target.delegate(n, "click", function (i) {
                        i.preventDefault(), t.prev()
                    }), this.target.bind("mouseover", function () {
                        t.stopTimer()
                    }), this.target.bind("mouseleave", function () {
                        t.startTimer()
                    }), this
                }
            }, {
                key: "slide", value: function i() {
                    var t = this, e = arguments.length <= 0 || void 0 === arguments[0] ? 0 : arguments[0];
                    if (this.pending)return !1;
                    this.pending = !0;
                    var i = this, n = parseInt(e, 10), s = this.slideItem, r = this.target.find(this.config.childrenItem), a = this.target.outerWidth(), o = r.length, l = o - 1, h = null, u = this.config.animateTime;
                    if (0 > n) {
                        n = l, h = r.first().clone();
                        var c = 0 - o * a;
                        s.css({left: c, width: a * (o + 1)}), s.append(h)
                    } else n > l && (h = r.first().clone(), s.css({width: a * (o + 1)}), s.append(h));
                    var f = this.config.beforeSlide;
                    f && "function" == typeof f && f.call(i, e);
                    var d = 0 - n * a;
                    return s.animate({left: d}, u, function () {
                        i.pending = !1, h && (h.remove(), s.css({width: a * o}), n > l && (n = 0, s.css({left: 0}))), t.index = n, t.renderPaginator();
                        var e = t.config.afterSlide;
                        e && "function" == typeof e && e.call(t, n)
                    }), this
                }
            }, {
                key: "next", value: function () {
                    var t = this.index + 1;
                    return this.slide(t), this
                }
            }, {
                key: "prev", value: function () {
                    var t = this.index - 1;
                    return this.slide(t), this
                }
            }, {
                key: "startTimer", value: function () {
                    var t = this;
                    return this.config.auto ? (this.timer = setTimeout(function () {
                        t.next(), t.startTimer()
                    }, this.config.interval), this) : !1
                }
            }, {
                key: "stopTimer", value: function () {
                    return this.config.auto ? (this.timer && (clearTimeout(this.timer), this.timer = null), this) : !1
                }
            }]), t
        }();
        i["default"] = r, t.exports = i["default"]
    }
});

