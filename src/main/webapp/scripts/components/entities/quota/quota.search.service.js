'use strict';

angular.module('stepApp')
    .factory('QuotaSearch', function ($resource) {
        return $resource('api/_search/quotas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
