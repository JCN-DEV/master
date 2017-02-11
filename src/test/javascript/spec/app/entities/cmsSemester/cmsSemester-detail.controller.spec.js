'use strict';

describe('CmsSemester Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCmsSemester, MockCmsCurriculum;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCmsSemester = jasmine.createSpy('MockCmsSemester');
        MockCmsCurriculum = jasmine.createSpy('MockCmsCurriculum');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CmsSemester': MockCmsSemester,
            'CmsCurriculum': MockCmsCurriculum
        };
        createController = function() {
            $injector.get('$controller')("CmsSemesterDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:cmsSemesterUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
