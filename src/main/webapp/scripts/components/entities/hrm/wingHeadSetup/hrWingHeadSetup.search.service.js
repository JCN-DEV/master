'use strict';

angular.module('stepApp')
    .factory('HrWingHeadSetupSearch', function ($resource) {
        return $resource('api/_search/hrWingHeadSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
