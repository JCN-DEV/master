'use strict';

angular.module('stepApp')
    .factory('DrugsSearch', function ($resource) {
        return $resource('api/_search/drugss/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
