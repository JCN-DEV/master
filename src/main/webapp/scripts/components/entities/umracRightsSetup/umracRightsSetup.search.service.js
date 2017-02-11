'use strict';

angular.module('stepApp')
    .factory('UmracRightsSetupSearch', function ($resource) {
        return $resource('api/_search/umracRightsSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
