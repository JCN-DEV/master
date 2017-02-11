'use strict';

angular.module('stepApp')
    .factory('TrainingInitializationInfoSearch', function ($resource) {
        return $resource('api/_search/trainingInitializationInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
