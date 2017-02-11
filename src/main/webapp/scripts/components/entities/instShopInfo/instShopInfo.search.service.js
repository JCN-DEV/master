'use strict';

angular.module('stepApp')
    .factory('InstShopInfoSearch', function ($resource) {
        return $resource('api/_search/instShopInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
