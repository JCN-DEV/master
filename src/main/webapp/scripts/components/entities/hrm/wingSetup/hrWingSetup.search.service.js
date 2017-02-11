'use strict';

angular.module('stepApp')
    .factory('HrWingSetupSearch', function ($resource) {
        return $resource('api/_search/hrWingSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
