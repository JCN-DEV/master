'use strict';

angular.module('stepApp')
    .factory('IncrementSetupSearch', function ($resource) {
        return $resource('api/_search/incrementSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
