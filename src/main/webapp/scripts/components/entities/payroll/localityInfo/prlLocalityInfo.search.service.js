'use strict';

angular.module('stepApp')
    .factory('PrlLocalityInfoSearch', function ($resource) {
        return $resource('api/_search/prlLocalityInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
