'use strict';

angular.module('stepApp')
    .factory('InstShopInfoTempSearch', function ($resource) {
        return $resource('api/_search/instShopInfoTemps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
