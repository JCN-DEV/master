'use strict';

describe('InsAcademicInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInsAcademicInfoTemp, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInsAcademicInfoTemp = jasmine.createSpy('MockInsAcademicInfoTemp');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InsAcademicInfoTemp': MockInsAcademicInfoTemp,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("InsAcademicInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:insAcademicInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
