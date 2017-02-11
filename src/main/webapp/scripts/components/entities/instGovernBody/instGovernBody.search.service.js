'use strict';

angular.module('stepApp')
    .factory('InstGovernBodySearch', function ($resource) {
        return $resource('api/_search/instGovernBodys/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
