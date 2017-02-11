'use strict';

angular.module('stepApp')
    .factory('ExternalCVSearch', function ($resource) {
        return $resource('api/_search/externalCVs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
