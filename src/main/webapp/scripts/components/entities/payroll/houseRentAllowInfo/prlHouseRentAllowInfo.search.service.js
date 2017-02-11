'use strict';

angular.module('stepApp')
    .factory('PrlHouseRentAllowInfoSearch', function ($resource) {
        return $resource('api/_search/prlHouseRentAllowInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
