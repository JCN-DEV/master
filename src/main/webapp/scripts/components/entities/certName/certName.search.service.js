'use strict';

angular.module('stepApp')
    .factory('CertNameSearch', function ($resource) {
        return $resource('api/_search/certNames/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
