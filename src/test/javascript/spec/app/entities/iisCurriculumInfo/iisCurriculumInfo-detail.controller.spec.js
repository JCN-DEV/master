'use strict';

describe('IisCurriculumInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockIisCurriculumInfo, MockCmsCurriculum;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockIisCurriculumInfo = jasmine.createSpy('MockIisCurriculumInfo');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'IisCurriculumInfo': MockIisCurriculumInfo,
            'CmsCurriculum': MockCmsCurriculum
        };
        createController = function() {
            $injector.get('$controller')("IisCurriculumInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:iisCurriculumInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
