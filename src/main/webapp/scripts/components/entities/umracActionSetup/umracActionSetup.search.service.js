'use strict';

angular.module('stepApp')
    .factory('UmracActionSetupSearch', function ($resource) {
        return $resource('api/_search/umracActionSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
