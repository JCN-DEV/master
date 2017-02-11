'use strict';

angular.module('stepApp')
    .factory('InstituteShopInfoSearch', function ($resource) {
        return $resource('api/_search/instituteShopInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
