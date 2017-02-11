'use strict';

angular.module('stepApp')
    .factory('InstEmplTrainingSearch', function ($resource) {
        return $resource('api/_search/instEmplTrainings/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmplTrainingCurrent', function ($resource) {
        return $resource('api/instEmplTrainingsCurrent', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
