'use strict';

angular.module('stepApp')
    .factory('TrainingHeadSetupSearch', function ($resource) {
        return $resource('api/_search/trainingHeadSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
