'use strict';

angular.module('stepApp')
    .factory('SisQuotaSearch', function ($resource) {
        return $resource('api/_search/sisQuotas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
