'use strict';

angular.module('stepApp')
    .factory('AlmDutySideSearch', function ($resource) {
        return $resource('api/_search/almDutySides/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
