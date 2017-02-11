'use strict';

angular.module('stepApp')
    .factory('TraineeEvaluationInfoSearch', function ($resource) {
        return $resource('api/_search/traineeEvaluationInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
