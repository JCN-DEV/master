'use strict';

angular.module('stepApp')
    .factory('TrainerEvaluationInfoSearch', function ($resource) {
        return $resource('api/_search/trainerEvaluationInfos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
